import { Entity, Column, PrimaryGeneratedColumn, CreateDateColumn } from 'typeorm';

@Entity('personality_results')
export class PersonalityResult {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ length: 4 })
  mbtiType: string;

  @Column({ type: 'text' })
  description: string;

  @Column({ type: 'jsonb' })
  answers: object;

  @CreateDateColumn()
  createdAt: Date;
}
