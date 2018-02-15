import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Professeur } from './professeur.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Professeur>;

@Injectable()
export class ProfesseurService {

    private resourceUrl =  SERVER_API_URL + 'api/professeurs';

    constructor(private http: HttpClient) { }

    create(professeur: Professeur): Observable<EntityResponseType> {
        const copy = this.convert(professeur);
        return this.http.post<Professeur>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(professeur: Professeur): Observable<EntityResponseType> {
        const copy = this.convert(professeur);
        return this.http.put<Professeur>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Professeur>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Professeur[]>> {
        const options = createRequestOption(req);
        return this.http.get<Professeur[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Professeur[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Professeur = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Professeur[]>): HttpResponse<Professeur[]> {
        const jsonResponse: Professeur[] = res.body;
        const body: Professeur[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Professeur.
     */
    private convertItemFromServer(professeur: Professeur): Professeur {
        const copy: Professeur = Object.assign({}, professeur);
        return copy;
    }

    /**
     * Convert a Professeur to a JSON which can be sent to the server.
     */
    private convert(professeur: Professeur): Professeur {
        const copy: Professeur = Object.assign({}, professeur);
        return copy;
    }
}
