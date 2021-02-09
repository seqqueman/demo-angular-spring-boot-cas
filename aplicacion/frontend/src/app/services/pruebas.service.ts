import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BASE_ENDPOINT } from '../config/app';
import { Prueba } from '../models/prueba';

@Injectable({
  providedIn: 'root'
})
export class PruebasService {

  private base_endpoint = BASE_ENDPOINT+"/prueba";
  private cabeceras: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  public listar(): Observable<Prueba[]>{
    return this.http.get<Prueba[]>(this.base_endpoint);
  }

  public ver(id: number): Observable<Prueba>{
    return this.http.get<Prueba>(`${this.base_endpoint}/${id}`);
  }

  public crear(prueba: Prueba): Observable<Prueba>{
    return this.http.post<Prueba>(this.base_endpoint, prueba, { headers: this.cabeceras });
  }

  public editar(prueba: Prueba): Observable<Prueba>{
    return this.http.put<Prueba>(`${this.base_endpoint}/${prueba.id}`, prueba, { headers: this.cabeceras });
  }

  public eliminar(id: number): Observable<void>{
    return this.http.delete<void>(`${this.base_endpoint}/${id}`);
  }

}
