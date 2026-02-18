import { Controller, Post, Get, Body } from '@nestjs/common';
import { ApiTags, ApiOperation } from '@nestjs/swagger';
import { PlantsService } from './plants.service';

@ApiTags('plants')
@Controller('plants')
export class PlantsController {
  constructor(private readonly plantsService: PlantsService) {}

  @Post('detect')
  @ApiOperation({ summary: 'Analyse une photo de plante' })
  async detectDisease(@Body() body: { image: string; mimeType?: string }) {
    const base64Clean = body.image.includes(',') ? body.image.split(',')[1] : body.image;
    return this.plantsService.detectDisease(base64Clean, body.mimeType);
  }

  @Get('history')
  @ApiOperation({ summary: 'Historique des détections' })
  getHistory() {
    return this.plantsService.getHistory();
  }
}
