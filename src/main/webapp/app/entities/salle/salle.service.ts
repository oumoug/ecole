import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Salle } from './salle.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Salle>;

@Injectable()
export class SalleService {

    private resourceUrl =  SERVER_API_URL + 'api/salles';

    constructor(private http: HttpClient) { }

    create(salle: Salle): Observable<EntityResponseType> {
        const copy = this.convert(salle);
        return this.http.post<Salle>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(salle: Salle): Observable<EntityResponseType> {
        const copy = this.convert(salle);
        return this.http.put<Salle>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Salle>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Salle[]>> {
        const options = createRequestOption(req);
        return this.http.get<Salle[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Salle[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Salle = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Salle[]>): HttpResponse<Salle[]> {
        const jsonResponse: Salle[] = res.body;
        const body: Salle[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Salle.
     */
    private convertItemFromServer(salle: Salle): Salle {
        const copy: Salle = Object.assign({}, salle);
        return copy;
    }

    /**
     * Convert a Salle to a JSON which can be sent to the server.
     */
    private convert(salle: Salle): Salle {
        const copy: Salle = Object.assign({}, salle);
        return copy;
    }
}
