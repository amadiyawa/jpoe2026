# CAHIER DES CHARGES - PROJET 1
## APPLICATION TEST DE PERSONNALITÉ AVEC IA

**Établissement :** [Nom de l'établissement]  
**Niveau :** Terminale TI  
**Encadreur :** [Votre nom]  
**Élèves :** 5 élèves  
**Durée :** 3 semaines  
**JPOE 2026 :** "La créativité à l'ère de l'IA : de l'artisanat à la haute technologie"

---

## 🎯 OBJECTIF

Développer une application mobile Android de test de personnalité MBTI utilisant l'intelligence artificielle pour générer des descriptions personnalisées et des recommandations de carrières adaptées au Cameroun.

---

## 📱 FONCTIONNALITÉS MVP

### ✅ Ce que l'app DOIT faire :

1. **Questionnaire MBTI** (30 questions)
   - Questions sur les préférences personnelles
   - Choix multiple (échelle 1-5)
   - Progression visuelle

2. **Calcul du type MBTI**
   - Algorithme de scoring (4 dimensions : E/I, S/N, T/F, J/P)
   - Résultat parmi 16 types (INTJ, ENFP, etc.)

3. **Description personnalisée par IA**
   - Google Gemini génère un texte unique
   - Forces, axes de développement, carrières au Cameroun
   - 200 mots environ en français

4. **Affichage des résultats**
   - Type de personnalité
   - Description complète
   - Recommandations

5. **Historique local**
   - Sauvegarde des tests passés
   - Consultation résultats précédents

### ❌ Ce que l'app ne fait PAS (MVP) :

- Comptes utilisateurs / authentification
- Synchronisation cloud
- Partage sur réseaux sociaux
- Chatbot conversationnel
- Statistiques avancées

---

## 🏗️ ARCHITECTURE TECHNIQUE

```
┌─────────────────────────────────────────────────────┐
│        APPLICATION ANDROID (Droidkotlin)            │
│                                                     │
│  Presentation → Domain → Data                       │
│  (UI Compose)   (Logique) (API/DB)                 │
└──────────────────────┬──────────────────────────────┘
                       │
                       │ HTTPS / REST API
                       │
┌──────────────────────▼──────────────────────────────┐
│           BACKEND (NestJS + TypeScript)             │
│                                                     │
│  Controllers → Services → Database                  │
│                    ↓                                │
│              Google Gemini API                      │
└──────────────────────┬──────────────────────────────┘
                       │
                  PostgreSQL
```

---

## 🔧 STACK TECHNIQUE

| Composant | Technologie | Pourquoi |
|-----------|-------------|----------|
| **Frontend** | Kotlin + Jetpack Compose | Interface moderne Android |
| **Architecture** | Clean Architecture (Droidkotlin) | Code organisé et maintenable |
| **Backend** | NestJS + TypeScript | Rapide, les élèves connaissent JS |
| **Base de données** | PostgreSQL | Stockage fiable |
| **Hébergement** | Digital Ocean | Serveur cloud |
| **IA** | Google Gemini API | Gratuit, génération texte |

---

## 👥 RÉPARTITION DES ÉQUIPES

### **Équipe Backend (2 élèves)**

**Responsabilités :**
- Créer les 30 questions MBTI dans un fichier TypeScript
- Compléter les endpoints API (avec templates fournis)
- Tester avec Postman

**Livrables :**
- Fichier `questions.data.ts` avec 30 questions
- Endpoint `GET /api/v1/questions` fonctionnel
- Endpoint `POST /api/v1/submit` fonctionnel

**Formation nécessaire :**
- TypeScript basique (2h)
- NestJS concepts (2h)
- Postman pour tests (1h)

---

### **Équipe Frontend (2 élèves)**

**Responsabilités :**
- Modifier l'interface Compose (couleurs, textes, layouts)
- Créer QuestionCard et ResultScreen
- Tester sur smartphones

**Livrables :**
- QuestionnaireScreen stylisé
- ResultScreen avec affichage du type MBTI
- Navigation fonctionnelle

**Formation nécessaire :**
- Kotlin basique (2h)
- Jetpack Compose (2h)
- Droidkotlin structure (1h)

---

### **Équipe Contenu (1 élève)**

**Responsabilités :**
- Rédiger les 30 questions MBTI
- Créer 16 descriptions de base (fallback)
- Tester l'application et documenter bugs

**Livrables :**
- Document avec 30 questions validées
- 16 descriptions MBTI (200 mots chacune)
- Liste de bugs trouvés

**Formation nécessaire :**
- Recherche MBTI (2h)
- Tests utilisateurs (1h)

---

## 📅 PLANNING 3 SEMAINES

### **Semaine 1 : Setup + Base**

**Lundi :**
- Installation Android Studio + Git
- Formation TypeScript/Kotlin (2h chacun)
- Attribution des tâches

**Mardi-Mercredi :**
- Backend : Créer fichier questions
- Frontend : Explorer Droidkotlin, modifier textes
- Contenu : Recherche MBTI, rédaction questions

**Jeudi-Vendredi :**
- Backend : Endpoint GET /questions
- Frontend : QuestionnaireScreen basique
- Contenu : Finaliser les 30 questions

---

### **Semaine 2 : Développement**

**Lundi-Mardi :**
- Backend : Endpoint POST /submit + algorithme MBTI
- Frontend : Affichage des questions + sélection réponses
- Contenu : Rédiger 5 descriptions MBTI

**Mercredi-Jeudi :**
- Backend : Intégration Gemini API
- Frontend : ResultScreen
- Contenu : Finaliser 16 descriptions

**Vendredi :**
- Intégration complète
- Tests ensemble
- Corrections bugs

---

### **Semaine 3 : Finalisation**

**Lundi-Mardi :**
- Tests sur smartphones réels
- Corrections bugs
- Optimisations

**Mercredi-Jeudi :**
- Préparation présentation PowerPoint
- Documentation technique
- Répétition démo

**Vendredi :**
- **Présentation JPOE 2026** 🎉

---

## 📦 LIVRABLES FINAUX

1. ✅ **Application Android** (.apk installable)
2. ✅ **Code source** (GitHub avec documentation)
3. ✅ **Base de données** (30 questions + structure)
4. ✅ **Rapport technique** (architecture, difficultés, solutions)
5. ✅ **Présentation PowerPoint** (démo + explications)

---

## 🎓 COMPÉTENCES ACQUISES

### **Pour tous les élèves :**
- Gestion de projet (planning, travail en équipe)
- Git et versioning
- Architecture Clean (concepts)
- APIs REST
- Intelligence artificielle (concepts et usage)

### **Équipe Backend :**
- TypeScript
- NestJS (controllers, services)
- PostgreSQL (bases)
- APIs externes (Gemini)

### **Équipe Frontend :**
- Kotlin
- Jetpack Compose
- Architecture Android
- Navigation

### **Équipe Contenu :**
- Recherche documentaire
- Rédaction technique
- Tests utilisateurs
- Psychométrie (concepts MBTI)

---

## ⚠️ DÉFIS ANTICIPÉS

| Défi | Solution |
|------|----------|
| **Pas d'internet à l'école** | Travail hors ligne, installation via USB, tests API à domicile |
| **Élèves débutants** | Templates de code fournis, pair programming, formation progressive |
| **Temps limité** | Focus MVP, fonctionnalités essentielles uniquement |
| **Debugging complexe** | Séances de débugging collectif, aide encadreur |

---

## ✅ CRITÈRES DE RÉUSSITE

Le projet est réussi si :

1. ✅ L'application compile et s'installe sur Android
2. ✅ Les 30 questions s'affichent correctement
3. ✅ Le calcul MBTI fonctionne (résultat parmi 16 types)
4. ✅ Gemini génère une description (même basique)
5. ✅ L'interface est présentable
6. ✅ La démo JPOE fonctionne sans crash
7. ✅ Chaque élève comprend son rôle et ce qu'il a appris

---

## 🚀 ÉVOLUTIONS FUTURES (POST-JPOE)

Après le JPOE, si le projet continue :

- Comptes utilisateurs avec authentification
- Statistiques des types MBTI au Cameroun
- Comparaison de compatibilité entre types
- Chatbot pour questions personnalisées
- Publication sur Google Play Store
- Partage des résultats
- Plus de langues (anglais, langues camerounaises)

---

**Document préparé le :** [Date]  
**Version :** MVP 1.0  
**Contact encadreur :** [Email/Téléphone]
