import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { IDealer, defaultValue } from 'app/shared/model/dealers/dealer.model';

export const ACTION_TYPES = {
  FETCH_DEALER_LIST: 'dealer/FETCH_DEALER_LIST',
  FETCH_DEALER: 'dealer/FETCH_DEALER',
  CREATE_DEALER: 'dealer/CREATE_DEALER',
  UPDATE_DEALER: 'dealer/UPDATE_DEALER',
  DELETE_DEALER: 'dealer/DELETE_DEALER',
  RESET: 'dealer/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDealer>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DealerState = Readonly<typeof initialState>;

// Reducer

export default (state: DealerState = initialState, action): DealerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DEALER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DEALER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DEALER):
    case REQUEST(ACTION_TYPES.UPDATE_DEALER):
    case REQUEST(ACTION_TYPES.DELETE_DEALER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DEALER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DEALER):
    case FAILURE(ACTION_TYPES.CREATE_DEALER):
    case FAILURE(ACTION_TYPES.UPDATE_DEALER):
    case FAILURE(ACTION_TYPES.DELETE_DEALER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEALER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEALER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DEALER):
    case SUCCESS(ACTION_TYPES.UPDATE_DEALER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DEALER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'services/dealers/api/dealers';

// Actions

export const getEntities: ICrudGetAllAction<IDealer> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DEALER_LIST,
    payload: axios.get<IDealer>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDealer> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DEALER,
    payload: axios.get<IDealer>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDealer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DEALER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDealer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DEALER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDealer> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DEALER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
