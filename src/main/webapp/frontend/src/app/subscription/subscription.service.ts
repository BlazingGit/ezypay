import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subscription } from './subscription';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class SubscriptionService {
  apiURL = 'http://localhost:8049/subscription';

  constructor(private http: HttpClient) { }

  addSubscription(model: Subscription): Observable<Subscription> {
    return this.http.post<Subscription>(this.apiURL + '/add', model);
  }
}
