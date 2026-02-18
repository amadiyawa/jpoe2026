import { Entity, Column, PrimaryGeneratedColumn, CreateDateColumn } from 'typeorm';

@Entity('plant_detections')
export class PlantDetection {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ length: 100 })
  plant: string;

  @Column({ length: 200 })
  disease: string;

  @Column({ type: 'int' })
  confidence: number;

  @Column({ length: 20, nullable: true })
  severity: string;

  @Column({ length: 20 })
  detectionMode: string;

  @Column({ type: 'jsonb', nullable: true })
  fullResult: object;

  @CreateDateColumn()
  createdAt: Date;
}
