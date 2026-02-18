import { NestFactory } from '@nestjs/core';
import { SwaggerModule, DocumentBuilder } from '@nestjs/swagger';
import { AppModule } from './app.module';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  app.setGlobalPrefix('api/v1');
  app.enableCors();

  const config = new DocumentBuilder()
    .setTitle('JPOE 2026 API')
    .setDescription(
      'Backend partagé — Projet 1 : Persome (MBTI) | Projet 2 : PlantDoc (Maladies plantes)'
    )
    .setVersion('1.0')
    .addTag('personality', 'Projet 1 - Test de personnalité MBTI')
    .addTag('plants', 'Projet 2 - Détection maladies plantes')
    .build();

  const document = SwaggerModule.createDocument(app, config);
  SwaggerModule.setup('api/docs', app, document);

  const port = process.env.PORT || 3000;
  await app.listen(port);

  console.log(`🚀 Backend JPOE 2026 démarré sur le port ${port}`);
  console.log(`📚 Documentation Swagger : http://localhost:${port}/api/docs`);
  console.log(`📱 Persome  : http://localhost:${port}/api/v1/personality/questions`);
  console.log(`🌿 PlantDoc : http://localhost:${port}/api/v1/plants/detect`);
}

bootstrap();
