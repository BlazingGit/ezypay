import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Subscription } from './subscription';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { AppDateAdapter, APP_DATE_FORMATS } from '../format-datepicker';
import { SubscriptionService } from './subscription.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TokenizeResult } from '@angular/compiler/src/ml_parser/lexer';

@Component({
  selector: 'app-subscription',
  templateUrl: './subscription.component.html',
  styleUrls: ['./subscription.component.css'],
  providers: [
    {provide: DateAdapter, useClass: AppDateAdapter},
    {provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS}
  ]
})
export class SubscriptionComponent implements OnInit {
  DAILY = 'DAILY';
  WEEKLY = 'WEEKLY'
  MONTHLY = 'MONTHLY';
  DAYS = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

  totalAmount = 0;
  dayOfDate = '';
  inputForm: FormGroup;

  constructor(private subscriptionService: SubscriptionService, public dialog: MatDialog) {}

  ngOnInit() {
    this.inputForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.maxLength(100)]),
      type: new FormControl(this.DAILY, [Validators.required, Validators.maxLength(50)]),
      dayOfDate: new FormControl(''),
      startDate: new FormControl(new Date(), [Validators.required]),
      endDate: new FormControl(new Date(), [Validators.required]),
      amount: new FormControl(1, [Validators.required, Validators.min(1), Validators.max(1000000)])
    });
    this.onChanges();
  }

  onChanges(): void {
    this.inputForm.get('type').valueChanges.subscribe(val => {
      if (val && this.inputForm.value.startDate) {
        const startDate = this.inputForm.value.startDate;
        this.dayOfDate = val === this.WEEKLY ? this.DAYS[startDate.getDay()] : startDate.getDate();
      } else {
        this.dayOfDate = '';
      }
    });

    this.inputForm.get('startDate').valueChanges.subscribe(val => { this.updateEndDate(val, true); });
    this.inputForm.get('endDate').valueChanges.subscribe(val => { this.updateEndDate(val, false); });
  }

  updateEndDate(value: any, isStart: boolean) {
    const startDate = isStart ? value : this.inputForm.value.startDate;
    const endDate = isStart ? this.inputForm.value.endDate : value;
    if (startDate && endDate && startDate > endDate) {
      this.inputForm.patchValue({endDate: startDate}, {emitEvent: false});
    }
    if (startDate && this.inputForm.value.type) {
      this.dayOfDate = this.inputForm.value.type === this.WEEKLY ? this.DAYS[startDate.getDay()] : startDate.getDate();
    } else {
      this.dayOfDate = '';
    }
  }

  public createSubscription = (formValue) => {
    if (this.inputForm.valid) {
      this.executeSubscriptionCreation(formValue);
    }
  }

  private executeSubscriptionCreation = (formValue: any) => {
    const subscription: Subscription = {
      customerName: formValue.name,
      type: formValue.type,
      dayOfDate: this.dayOfDate,
      startDate: this.formatDate(formValue.startDate),
      endDate: this.formatDate(formValue.endDate),
      amount: formValue.amount,
      totalAmount: 0,
      invoiceDates: []
    }
    this.subscriptionService.addSubscription(subscription).subscribe(resp => {
      if (resp) {
        this.openDialog(resp);
      }
    });
  }

  openDialog(model: Subscription): void {
    const dialogRef = this.dialog.open(SubscriptionDialog, {
      width: '500px',
      data: model
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed ' + result);
    });
  }

  private formatDate(input: Date): string {
    let dd = input.getDate().toString();
    let mm = (input.getMonth() + 1).toString();
    const yyyy = input.getFullYear();
    if (input.getDate() < 10) {
      dd = '0' + dd;
    }

    if ((input.getMonth() + 1) < 10) {
      mm = '0' + mm;
    }
    return (dd + '/' + mm + '/' + yyyy);
  }

  public hasError = (controlName: string, errorName: string) =>{
    return this.inputForm.controls[controlName].hasError(errorName);
  }
}

@Component({
  selector: 'app-subscription-dialog',
  templateUrl: './subscription.dialog.html',
})
export class SubscriptionDialog {
  constructor(public dialogRef: MatDialogRef<SubscriptionDialog>, @Inject(MAT_DIALOG_DATA) public data: Subscription) {}
  onNoClick(): void {
    this.dialogRef.close();
  }

  displayDates(input: string[]) {
    let result = '';
    let first = true;
    input.forEach(e => {
      result += first ? e : (',  ' + e);
      first = false;
    });
    return result;
  }
}
