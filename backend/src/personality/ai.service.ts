import { Injectable } from '@nestjs/common';
import Anthropic from '@anthropic-ai/sdk';

@Injectable()
export class PersonalityAiService {
    private readonly anthropic = new Anthropic({
        apiKey: process.env.ANTHROPIC_API_KEY
    });

    async generateDescription(
        mbtiType: string,
        firstName: string,
        age: number,
        city: string,
        situation: string
    ): Promise<string> {
        try {
            const situationLabel = this.getSituationLabel(situation);

            const prompt = `
Tu es un expert en psychologie MBTI spécialisé dans le contexte camerounais.

Génère une description personnalisée pour ${firstName}, ${age} ans, ${situationLabel} à ${city}.
Son type de personnalité MBTI est : ${mbtiType}.

La description doit contenir exactement 4 sections en français :
👤 Qui tu es (40 mots) : traits principaux du type ${mbtiType}
💪 Tes forces (50 mots) : 3-4 points forts concrets
🌱 Axes de développement (50 mots) : 2-3 axes d'amélioration
🎯 Carrières recommandées (60 mots) : métiers adaptés au contexte camerounais (CAMTEL, Orange, startups Douala/Yaoundé, fonction publique, entrepreneuriat)

Ton est encourageant et direct. Utilise "tu" et inclus le prénom ${firstName} au moins une fois.
200 mots maximum au total.
      `;

            const message = await this.anthropic.messages.create({
                model: 'claude-opus-4-6',
                max_tokens: 500,
                messages: [{ role: 'user', content: prompt }]
            });

            return (message.content[0] as { text: string }).text;

        } catch (error) {
            console.error('Claude API error:', error.message);
            return this.getFallbackDescription(mbtiType, firstName);
        }
    }

    // Labels lisibles pour chaque situation
    private getSituationLabel(situation: string): string {
        const labels: Record<string, string> = {
            'STUDENT': 'étudiant(e)',
            'EMPLOYED': 'salarié(e)',
            'SELF_EMPLOYED': 'indépendant(e)',
            'SEEKING': 'en recherche d\'emploi'
        };
        return labels[situation] ?? situation;
    }

    // Fallback si Claude indisponible
    private getFallbackDescription(mbtiType: string, firstName: string): string {
        return `${firstName}, ton type ${mbtiType} révèle une personnalité unique avec des forces remarquables. Consulte notre guide complet pour découvrir ton profil détaillé.`;
    }
}