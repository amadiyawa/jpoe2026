import { Injectable } from '@nestjs/common';
import { GoogleGenerativeAI } from '@google/generative-ai';

@Injectable()
export class PersonalityAiService {
  private readonly gemini = new GoogleGenerativeAI(process.env.GEMINI_API_KEY);

  async generateDescription(mbtiType: string, scores: any): Promise<string> {
    try {
      const model = this.gemini.getGenerativeModel({ model: 'gemini-pro-latest' });
      const prompt = `Génère une description personnalisée pour le type MBTI ${mbtiType} en français, 200 mots max.`;
      const result = await model.generateContent(prompt);
      return result.response.text();
    } catch (error) {
      console.error('Gemini error:', error.message);
      return this.getFallbackDescription(mbtiType);
    }
  }

  private getFallbackDescription(mbtiType: string): string {
    const fallbacks: Record<string, string> = {
      'INTJ': 'Stratège visionnaire...',
      'INFP': 'Idéaliste créatif...',
    };
    return fallbacks[mbtiType] || `Type ${mbtiType}`;
  }
}
