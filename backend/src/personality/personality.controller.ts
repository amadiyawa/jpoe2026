import { Controller, Get, Post, Body } from '@nestjs/common';
import { ApiTags, ApiOperation } from '@nestjs/swagger';
import { PersonalityService } from './personality.service';

@ApiTags('personality')
@Controller('personality')
export class PersonalityController {
  constructor(private readonly personalityService: PersonalityService) {}

  @Get('questions')
  @ApiOperation({ summary: 'Récupère les 30 questions MBTI' })
  getQuestions() {
    return this.personalityService.getQuestions();
  }

  @Post('submit')
  @ApiOperation({ summary: 'Soumet les réponses et retourne le résultat' })
  async submitAnswers(@Body() body: { answers: Record<string, 'A' | 'B'> }) {
      if (!body || !body.answers) {
          throw new Error('Le champ "answers" est requis dans le body');
      }
      return this.personalityService.submitAnswers(body.answers);
  }
}
