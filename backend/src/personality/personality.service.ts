import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { PersonalityResult } from './entities/personality-result.entity';
import { MBTI_QUESTIONS } from './questions.data';
import { calculateMbtiType, Answers } from './mbti.calculator';
import { PersonalityAiService } from './ai.service';

@Injectable()
export class PersonalityService {
  constructor(
    @InjectRepository(PersonalityResult)
    private readonly resultsRepository: Repository<PersonalityResult>,
    private readonly aiService: PersonalityAiService,
  ) {}

  getQuestions() {
    return MBTI_QUESTIONS;
  }

  async submitAnswers(answers: Answers) {
    const { type, scores } = calculateMbtiType(answers);
    const description = await this.aiService.generateDescription(type, scores);
    
    const result = this.resultsRepository.create({
      mbtiType: type,
      description,
      answers,
    });
    await this.resultsRepository.save(result);

    return {
      id: result.id,
      mbtiType: type,
      description,
      scores,
      createdAt: result.createdAt,
    };
  }
}
