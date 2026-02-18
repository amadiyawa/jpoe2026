export type Answers = Record<string, 'A' | 'B'>;

export interface MbtiResult {
  type: string;
  scores: {
    EI: { E: number; I: number };
    SN: { S: number; N: number };
    TF: { T: number; F: number };
    JP: { J: number; P: number };
  };
}

export function calculateMbtiType(answers: Answers): MbtiResult {
  const scores = {
    EI: { E: 0, I: 0 },
    SN: { S: 0, N: 0 },
    TF: { T: 0, F: 0 },
    JP: { J: 0, P: 0 },
  };

  for (const [questionId, answer] of Object.entries(answers)) {
    const id = parseInt(questionId);

    if (id >= 1 && id <= 10) {
      if (answer === 'A') scores.EI.E++;
      else scores.EI.I++;
    } else if (id >= 11 && id <= 17) {
      if (answer === 'A') scores.SN.S++;
      else scores.SN.N++;
    } else if (id >= 18 && id <= 21) {
      if (answer === 'A') scores.TF.T++;
      else scores.TF.F++;
    } else if (id >= 22 && id <= 30) {
      if (answer === 'A') scores.JP.J++;
      else scores.JP.P++;
    }
  }

  const type = [
    scores.EI.E >= scores.EI.I ? 'E' : 'I',
    scores.SN.S >= scores.SN.N ? 'S' : 'N',
    scores.TF.T >= scores.TF.F ? 'T' : 'F',
    scores.JP.J >= scores.JP.P ? 'J' : 'P',
  ].join('');

  return { type, scores };
}
