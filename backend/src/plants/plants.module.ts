import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { PlantsController } from './plants.controller';
import { PlantsService } from './plants.service';
import { AiVisionService } from './ai-vision.service';
import { PlantDetection } from './entities/plant-detection.entity';

@Module({
  imports: [TypeOrmModule.forFeature([PlantDetection])],
  controllers: [PlantsController],
  providers: [PlantsService, AiVisionService],
})
export class PlantsModule {}
