import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Car from './car';
import CarDetail from './car-detail';
import CarUpdate from './car-update';
import CarDeleteDialog from './car-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CarDetail} />
      <ErrorBoundaryRoute path={match.url} component={Car} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CarDeleteDialog} />
  </>
);

export default Routes;
