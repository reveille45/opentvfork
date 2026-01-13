import { Directive, forwardRef, Input, OnChanges, SimpleChanges, HostListener } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator, NgControl } from '@angular/forms';

@Directive({
  selector: '[empty]',
  providers: [
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => NotEmptyValidatorDirective),
      multi: true
    }
  ]
})
export class NotEmptyValidatorDirective implements Validator, OnChanges {

  @Input('emptyDisabled')
  disabled = false;
  private onChange: (() => void) | undefined;
  private control: AbstractControl | null = null;

  constructor() {}

  validate(control: AbstractControl): ValidationErrors | null {
    this.control = control;
    if (this.disabled === true)
      return null;
    const value = control.value;
    if (value === null || value === undefined || (typeof value === 'string' && !value.trim())) {
      return { 'empty': true };
    }
    return null;
  }

  registerOnValidatorChange(fn: () => void): void {
    this.onChange = fn;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if ('disabled' in changes && this.onChange) {
      this.onChange();
    }
  }

  // Force validation on input events for Android WebView compatibility
  @HostListener('input')
  @HostListener('blur')
  onInputChange(): void {
    if (this.onChange) {
      this.onChange();
    }
    if (this.control) {
      this.control.updateValueAndValidity();
    }
  }
}
