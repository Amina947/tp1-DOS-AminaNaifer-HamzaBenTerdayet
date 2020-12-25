import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrdonnance, Ordonnance } from 'app/shared/model/ordonnance.model';
import { OrdonnanceService } from './ordonnance.service';

@Component({
  selector: 'jhi-ordonnance-update',
  templateUrl: './ordonnance-update.component.html',
})
export class OrdonnanceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numero: [],
    medicament: [],
  });

  constructor(protected ordonnanceService: OrdonnanceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ordonnance }) => {
      this.updateForm(ordonnance);
    });
  }

  updateForm(ordonnance: IOrdonnance): void {
    this.editForm.patchValue({
      id: ordonnance.id,
      numero: ordonnance.numero,
      medicament: ordonnance.medicament,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ordonnance = this.createFromForm();
    if (ordonnance.id !== undefined) {
      this.subscribeToSaveResponse(this.ordonnanceService.update(ordonnance));
    } else {
      this.subscribeToSaveResponse(this.ordonnanceService.create(ordonnance));
    }
  }

  private createFromForm(): IOrdonnance {
    return {
      ...new Ordonnance(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      medicament: this.editForm.get(['medicament'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrdonnance>>): void {
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
}
