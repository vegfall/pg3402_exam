import { RevealAlternative } from "./revealAlternative";

export interface RevealQuestion {
  questionText: string;
  alternatives: RevealAlternative[];
  chosenAlternativeKey: number;
}
