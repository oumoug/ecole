import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Cours } from './cours.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Cours>;

@Injectable()
export class CoursService {

    private resourceUrl =  SERVER_API_URL + 'api/cours';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(cours: Cours): Observable<EntityResponseType> {
        const copy = this.convert(cours);
        return this.http.post<Cours>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(cours: Cours): Observable<EntityResponseType> {
        const copy = this.convert(cours);
        return this.http.put<Cours>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Cours>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Cours[]>> {
        const options = createRequestOption(req);
        return this.http.get<Cours[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Cours[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Cours = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Cours[]>): HttpResponse<Cours[]> {
        const jsonResponse: Cours[] = res.body;
        const body: Cours[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Cours.
     */
    private convertItemFromServer(cours: Cours): Cours {
        const copy: Cours = Object.assign({}, cours);
        copy.date = this.dateUtils
            .convertLocalDateFromServer(cours.date);
        copy.heure = this.dateUtils
            .convertDateTimeFromServer(cours.heure);
        return copy;
    }

    /**
     * Convert a Cours to a JSON which can be sent to the server.
     */
    private convert(cours: Cours): Cours {
        const copy: Cours = Object.assign({}, cours);
        copy.date = this.dateUtils
            .convertLocalDateToServer(cours.date);

        copy.heure = this.dateUtils.toDate(cours.heure);
        return copy;
    }
}
