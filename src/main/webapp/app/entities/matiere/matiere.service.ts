import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Matiere } from './matiere.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Matiere>;

@Injectable()
export class MatiereService {

    private resourceUrl =  SERVER_API_URL + 'api/matieres';

    constructor(private http: HttpClient) { }

    create(matiere: Matiere): Observable<EntityResponseType> {
        const copy = this.convert(matiere);
        return this.http.post<Matiere>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(matiere: Matiere): Observable<EntityResponseType> {
        const copy = this.convert(matiere);
        return this.http.put<Matiere>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Matiere>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Matiere[]>> {
        const options = createRequestOption(req);
        return this.http.get<Matiere[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Matiere[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Matiere = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Matiere[]>): HttpResponse<Matiere[]> {
        const jsonResponse: Matiere[] = res.body;
        const body: Matiere[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Matiere.
     */
    private convertItemFromServer(matiere: Matiere): Matiere {
        const copy: Matiere = Object.assign({}, matiere);
        return copy;
    }

    /**
     * Convert a Matiere to a JSON which can be sent to the server.
     */
    private convert(matiere: Matiere): Matiere {
        const copy: Matiere = Object.assign({}, matiere);
        return copy;
    }
}
