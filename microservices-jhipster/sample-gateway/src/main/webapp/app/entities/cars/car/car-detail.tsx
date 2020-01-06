import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './car.reducer';
import { ICar } from 'app/shared/model/cars/car.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICarDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CarDetail extends React.Component<ICarDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { carEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleGatewayApp.carsCar.detail.title">Car</Translate> [<b>{carEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="make">
                <Translate contentKey="sampleGatewayApp.carsCar.make">Make</Translate>
              </span>
            </dt>
            <dd>{carEntity.make}</dd>
            <dt>
              <span id="model">
                <Translate contentKey="sampleGatewayApp.carsCar.model">Model</Translate>
              </span>
            </dt>
            <dd>{carEntity.model}</dd>
            <dt>
              <span id="year">
                <Translate contentKey="sampleGatewayApp.carsCar.year">Year</Translate>
              </span>
            </dt>
            <dd>{carEntity.year}</dd>
          </dl>
          <Button tag={Link} to="/entity/car" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/car/${carEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ car }: IRootState) => ({
  carEntity: car.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CarDetail);
