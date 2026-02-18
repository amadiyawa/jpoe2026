# Backend JPOE 2026 - NestJS

Backend partagé pour Projet 1 (Persome) et Projet 2 (PlantDoc).

## Installation

```bash
npm install
cp .env.example .env
# Renseigner DATABASE_* et GEMINI_API_KEY dans .env
npm run start:dev
```

## Documentation

Swagger UI: **http://localhost:3000/api/docs**

## Routes

### Projet 1 - Persome
- `GET /api/v1/personality/questions` - Les 30 questions MBTI
- `POST /api/v1/personality/submit` - Soumettre réponses

### Projet 2 - PlantDoc  
- `POST /api/v1/plants/detect` - Analyser photo
- `GET /api/v1/plants/history` - Historique

## Structure

```
src/
├── main.ts              ← Démarrage + Swagger
├── app.module.ts        ← Module racine
├── personality/         ← Projet 1
│   ├── questions.data.ts     (TODO: compléter)
│   ├── mbti.calculator.ts
│   ├── ai.service.ts
│   ├── personality.service.ts
│   ├── personality.controller.ts
│   ├── personality.module.ts
│   └── entities/
└── plants/              ← Projet 2
    ├── ai-vision.service.ts
    ├── plants.service.ts
    ├── plants.controller.ts
    ├── plants.module.ts
    └── entities/
```

## Travail élèves

**Équipe Backend P1:**
- Compléter `questions.data.ts` (questions 6-30)
- Tester endpoints avec Swagger

**Équipe Backend P2:**
- Tester `/plants/detect` avec photos
- Améliorer prompts Gemini si besoin
