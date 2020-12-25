import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPatient, Patient } from 'app/shared/model/patient.model';
import { PatientService } from './patient.service';
import { IMedecin } from 'app/shared/model/medecin.model';
import { MedecinService } from 'app/entities/medecin/medecin.service';
import { IOrdonnance } from 'app/shared/model/ordonnance.model';
import { OrdonnanceService } from 'app/entities/ordonnance/ordonnance.service';

type SelectableEntity = IMedecin | IOrdonnance;

@Component({
  selector: 'jhi-patient-update',
  templateUrl: './patient-update.component.html',
})
export class PatientUpdateComponent implements OnInit {
  isSaving = false;
  medecins: IMedecin[] = [];
  ordonnances: IOrdonnance[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    tel: [],
    medecin: [],
    ordonnance: [],
  });

  constructor(
    protected patientService: PatientService,
    protected medecinService: MedecinService,
    protected ordonnanceService: OrdonnanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patient }) => {
      this.updateForm(patient);

      this.medecinService.query().subscribe((res: HttpResponse<IMedecin[]>) => (this.medecins = res.body || []));

      this.ordonnanceService
        .query({ filter: 'patient-is-null' })
        .pipe(
          map((res: HttpResponse<IOrdonnance[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IOrdonnance[]) => {
          if (!patient.ordonnance || !patient.ordonnance.id) {
            this.ordonnances = resBody;
          } else {
            this.ordonnanceService
              .find(patient.ordonnance.id)
              .pipe(
                map((subRes: HttpResponse<IOrdonnance>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IOrdonnance[]) => (this.ordonnances = concatRes));
          }
        });
    });
  }

  updateForm(patient: IPatient): void {
    this.editForm.patchValue({
      id: patient.id,
      nom: patient.nom,
      tel: patient.tel,
      medecin: patient.medecin,
      ordonnance: patient.ordonnance,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const patient = this.createFromForm();
    if (patient.id !== undefined) {
      this.subscribeToSaveResponse(this.patientService.update(patient));
    } else {
      this.subscribeToSaveResponse(this.patientService.create(patient));
    }
  }

  private createFromForm(): IPatient {
    return {
      ...new Patient(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      tel: this.editForm.get(['tel'])!.value,
      medecin: this.editForm.get(['medecin'])!.value,
      ordonnance: this.editForm.get(['ordonnance'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatient>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
