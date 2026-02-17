# JPOE 2026 - Projets IA

> Thème : "La créativité à l'ère de l'IA : de l'artisanat à la haute technologie"

## Structure

| Dossier | Description |
|---------|-------------|
| `persome/` | App Android - Test de personnalité MBTI (Projet 1) |
| `plantdoc/` | App Android - Détection maladies plantes (Projet 2) |
| `backend/` | Backend NestJS partagé |
| `docs/` | CDC, budgets, architecture |

## Stack Technique

- **Frontend** : Kotlin + Jetpack Compose (Droidkotlin)
- **Backend** : NestJS + TypeScript
- **Base de données** : PostgreSQL (Digital Ocean)
- **IA** : Google Gemini API + TensorFlow Lite

## Projets

### Projet 1 - Persome
Application de test de personnalité MBTI avec descriptions générées par Google Gemini.
- 5 élèves | 3 semaines

### Projet 2 - PlantDoc  
Détection de maladies des plantes via TensorFlow Lite (offline) + Gemini Vision (online).
- 7 élèves | 3 semaines

## Lancer le backend
```bash
cd backend
npm install
cp .env.example .env   # Renseigner les variables
npm run start:dev
```
