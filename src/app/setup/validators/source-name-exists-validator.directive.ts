import { Directive, forwardRef } from '@angular/core';
import { AbstractControl, AsyncValidator, NG_ASYNC_VALIDATORS, ValidationErrors } from '@angular/forms';
import { invoke } from '@tauri-apps/api/core';
import { from, map, Observable, of, switchMap, timer, catchError, timeout, take } from 'rxjs';

@Directive({
  selector: '[source-name-exists]',
  providers: [
    {
      provide: NG_ASYNC_VALIDATORS,
      useExisting: forwardRef(() => SourceNameExistsValidator),
      multi: true,
    },
  ],
})

export class SourceNameExistsValidator implements AsyncValidator {
  constructor() {}

  validate(control: AbstractControl): Observable<ValidationErrors | null> {
    let value = control.value?.trim();
    if (!value) {
      return of(null); // No validation needed if the field is empty
    }
    return timer(300).pipe(
      switchMap(() =>
        from(invoke<boolean>("source_name_exists", { name: value })).pipe(
          // Add timeout to prevent hanging in ng-pending indefinitely
          timeout(2000),
          // Map successful result
          map(exists => (exists === true ? { sourceNameExists: true } : null)),
          // Catch errors (including timeout) and treat as valid to unblock UI
          catchError((err) => {
            console.warn("Source name check failed or timed out:", err);
            return of(null);
          }),
          // Ensure observable completes
          take(1)
        )
      )
    );
  }
}
