import { Component, OnInit } from '@angular/core';
import { Prueba } from 'src/app/models/prueba';
import { PruebasService } from 'src/app/services/pruebas.service';

@Component({
  selector: 'app-segunda',
  templateUrl: './segunda.component.html',
  styleUrls: ['./segunda.component.css']
})
export class SegundaComponent implements OnInit {

  titulo = 'Titulo de la segunda pagina';
  pruebas: Prueba[] = [];

  constructor(private service: PruebasService) { }

  ngOnInit(): void {
    this.service.listar().subscribe(lprueba => this.pruebas = lprueba);
  }

}
