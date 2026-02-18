import { Injectable } from '@nestjs/common';
import { GoogleGenerativeAI } from '@google/generative-ai';

@Injectable()
export class AiVisionService {
  private readonly gemini = new GoogleGenerativeAI(process.env.GEMINI_API_KEY);

  async analyzePlantImage(imageBase64: string, mimeType = 'image/jpeg') {
    try {
      const model = this.gemini.getGenerativeModel({ model: 'gemini-2.5-flash' });
      const prompt = 'Analyse cette image de plante et retourne un diagnostic JSON.';
      
      const result = await model.generateContent([prompt, {
        inlineData: { data: imageBase64, mimeType }
      }]);
      
      return JSON.parse(result.response.text());
    } catch (error) {
      return { plant: 'Inconnu', disease: 'Erreur', confidence: 0 };
    }
  }
}
