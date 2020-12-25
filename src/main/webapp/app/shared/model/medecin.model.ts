import { IPatient } from 'app/shared/model/patient.model';

export interface IMedecin {
  id?: number;
  nom?: string;
  specialite?: string;
  patients?: IPatient[];
}

export class Medecin implements IMedecin {
  constructor(public id?: number, public nom?: string, public specialite?: string, public patients?: IPatient[]) {}
}
