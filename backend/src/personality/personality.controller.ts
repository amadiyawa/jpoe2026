import { Controller, Post, Body } from '@nestjs/common';
import { ApiTags, ApiOperation, ApiBody } from '@nestjs/swagger';
import { PersonalityService } from './personality.service';
import { DescribeDto } from './dto/describe.dto';

@ApiTags('personality')
@Controller('api/v1/personality')
export class PersonalityController {
    constructor(private readonly personalityService: PersonalityService) {}

    @Post('describe')
    @ApiOperation({ summary: 'Génère une description IA pour un type MBTI' })
    @ApiBody({ type: DescribeDto })
    async generateDescription(@Body() body: DescribeDto) {
        return this.personalityService.generateDescription(body);
    }
}