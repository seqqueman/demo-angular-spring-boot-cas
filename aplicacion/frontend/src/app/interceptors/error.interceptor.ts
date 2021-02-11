import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';



@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            catchError((error: HttpErrorResponse) => {
                if((error.status === 0 && error.url && error.url.includes('/back')) || error.status === 401 || error.status === 403) {

                    // window.location.href = 'https://pase-pru.carm.es/pase/login?service=http%3A%2F%2Flocalhost%3A4200%3Asegunda';
                    window.location.href = 'https://pase-pru.carm.es/pase/login?service=http%3A%2F%2Flocalhost%3A8080%2Fdemo-angular%2Flogin%2Fcas';
                    // https://pase-pru.carm.es/pase/login?service=https%3A%2F%2Fdelfos-pru.carm.es%2Fdelfos%2Flogin%2Fcas                  
                }
                return throwError(error);
            })
        );
    }

}
