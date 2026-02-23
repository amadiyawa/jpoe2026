import { ApiProperty } from '@nestjs/swagger';

export class DescribeDto {
    @ApiProperty({ example: 'INTJ', description: 'Type MBTI (4 lettres)' })
    mbtiType: string;

    @ApiProperty({ example: 'Jean', description: 'Prénom de l\'utilisateur' })
    firstName: string;

    @ApiProperty({ example: 17, description: 'Âge (10-80)' })
    age: number;

    @ApiProperty({ example: 'Yaoundé', description: 'Ville de résidence' })
    city: string;

    @ApiProperty({
        example: 'STUDENT',
        description: 'Situation professionnelle',
        enum: ['STUDENT', 'EMPLOYED', 'SELF_EMPLOYED', 'SEEKING']
    })
    situation: string;
}