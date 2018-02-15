import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Adresse } from './adresse.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Adresse>;

@Injectable()
export class AdresseService {

    private resourceUrl =  SERVER_API_URL + 'api/adresses';

    constructor(private http: HttpClient) { }

    create(adresse: Adresse): Observable<EntityResponseType> {
        const copy = this.convert(adresse);
        return this.http.post<Adresse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(adresse: Adresse): Observable<EntityResponseType> {
        const copy = this.convert(adresse);
        return this.http.put<Adresse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Adresse>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Adresse[]>> {
        const options = createRequestOption(req);
        return this.http.get<Adresse[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Adresse[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Adresse = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Adresse[]>): HttpResponse<Adresse[]> {
        const jsonResponse: Adresse[] = res.body;
        const body: Adresse[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Adresse.
     */
    private convertItemFromServer(adresse: Adresse): Adresse {
        const copy: Adresse = Object.assign({}, adresse);
        return copy;
    }

    /**
     * Convert a Adresse to a JSON which can be sent to the server.
     */
    private convert(adresse: Adresse): Adresse {
        const copy: Adresse = Object.assign({}, adresse);
        return copy;
    }
}
