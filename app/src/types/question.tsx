import { Alternative } from "./alternative";

export default interface Question {
  questionKey: number;
  questionText: string;
  alternatives: Alternative[];
}
