import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Dealer from './dealer';
import DealerDetail from './dealer-detail';
import DealerUpdate from './dealer-update';
import DealerDeleteDialog from './dealer-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DealerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DealerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DealerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Dealer} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DealerDeleteDialog} />
  </>
);

export default Routes;
