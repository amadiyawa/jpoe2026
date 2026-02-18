import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { PlantDetection } from './entities/plant-detection.entity';
import { AiVisionService } from './ai-vision.service';

@Injectable()
export class PlantsService {
  constructor(
    @InjectRepository(PlantDetection)
    private readonly detectionsRepository: Repository<PlantDetection>,
    private readonly aiVisionService: AiVisionService,
  ) {}

  async detectDisease(imageBase64: string, mimeType = 'image/jpeg') {
    const geminiResult: any = await this.aiVisionService.analyzePlantImage(imageBase64, mimeType);
    
    const detection = this.detectionsRepository.create({
      plant: geminiResult.plant || 'Inconnu',
      disease: geminiResult.disease || 'Inconnu',
      confidence: geminiResult.confidence || 0,
      severity: geminiResult.severity || 'aucun',
      detectionMode: 'gemini',
      fullResult: geminiResult,
    });
    await this.detectionsRepository.save(detection);

    return { id: detection.id, detectionMode: 'gemini', createdAt: detection.createdAt, ...geminiResult };
  }

  async getHistory(limit = 10) {
    return this.detectionsRepository.find({ order: { createdAt: 'DESC' }, take: limit });
  }
}
