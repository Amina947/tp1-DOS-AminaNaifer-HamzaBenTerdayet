import { IPatient } from 'app/shared/model/patient.model';

export interface IOrdonnance {
  id?: number;
  numero?: number;
  medicament?: string;
  patient?: IPatient;
}

export class Ordonnance implements IOrdonnance {
  constructor(public id?: number, public numero?: number, public medicament?: string, public patient?: IPatient) {}
}
