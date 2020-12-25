import { IMedecin } from 'app/shared/model/medecin.model';
import { IOrdonnance } from 'app/shared/model/ordonnance.model';

export interface IPatient {
  id?: number;
  nom?: string;
  tel?: number;
  medecin?: IMedecin;
  ordonnance?: IOrdonnance;
}

export class Patient implements IPatient {
  constructor(public id?: number, public nom?: string, public tel?: number, public medecin?: IMedecin, public ordonnance?: IOrdonnance) {}
}
