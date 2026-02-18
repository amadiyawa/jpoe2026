export interface Question {
  id: number;
  dimension: 'EI' | 'SN' | 'TF' | 'JP';
  text: string;
  optionA: string;
  optionB: string;
}

export const MBTI_QUESTIONS: Question[] = [
  {
    id: 1,
    dimension: 'EI',
    text: "Lors d'une fête ou d'une réunion, vous êtes plutôt quelqu'un qui :",
    optionA: "Parle à beaucoup de personnes, même des inconnus",
    optionB: "Reste avec quelques personnes que vous connaissez bien",
  },
  {
    id: 2,
    dimension: 'EI',
    text: "Après une longue journée de travail, pour vous ressourcer, vous préférez :",
    optionA: "Sortir avec des amis ou appeler quelqu'un",
    optionB: "Rester seul(e) chez vous au calme",
  },
  {
    id: 3,
    dimension: 'EI',
    text: "Quand vous avez un problème à résoudre, votre premier réflexe est de :",
    optionA: "En parler à quelqu'un pour avoir des avis",
    optionB: "Y réfléchir seul(e) d'abord",
  },
  {
    id: 4,
    dimension: 'EI',
    text: "Dans un groupe de travail, vous êtes plutôt :",
    optionA: "Celui/celle qui anime les discussions",
    optionB: "Celui/celle qui écoute et réfléchit",
  },
  {
    id: 5,
    dimension: 'EI',
    text: "Vous vous sentez le plus à l'aise :",
    optionA: "Dans un endroit animé avec beaucoup de monde",
    optionB: "Dans un endroit calme avec peu de personnes",
  },
  // TODO: Questions 6-30 à compléter par les élèves
];
