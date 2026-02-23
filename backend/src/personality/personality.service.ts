import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { PersonalityResult } from './entities/personality-result.entity';
import { PersonalityAiService } from './ai.service';

@Injectable()
export class PersonalityService {
    constructor(
        @InjectRepository(PersonalityResult)
        private readonly resultsRepository: Repository<PersonalityResult>,
        private readonly aiService: PersonalityAiService,
    ) {}

    async generateDescription(params: {
        mbtiType: string;
        firstName: string;
        age: number;
        city: string;
        situation: string;
    }) {
        const { mbtiType, firstName, age, city, situation } = params;

        // Génère la description via Gemini
        const description = await this.aiService.generateDescription(
            mbtiType,
            firstName,
            age,
            city,
            situation
        );

        // Sauvegarde en base pour statistiques futures
        const result = this.resultsRepository.create({
            mbtiType,
            description,
            userInfo: { firstName, age, city, situation }
        });
        await this.resultsRepository.save(result);

        return {
            mbtiType,
            description,
            createdAt: result.createdAt
        };
    }
}