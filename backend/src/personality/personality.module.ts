import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { PersonalityController } from './personality.controller';
import { PersonalityService } from './personality.service';
import { PersonalityAiService } from './ai.service';
import { PersonalityResult } from './entities/personality-result.entity';

@Module({
  imports: [TypeOrmModule.forFeature([PersonalityResult])],
  controllers: [PersonalityController],
  providers: [PersonalityService, PersonalityAiService],
})
export class PersonalityModule {}
