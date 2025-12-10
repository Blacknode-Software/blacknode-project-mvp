import { UnixTimestamp } from '@/utils';
import { userApi, userUi, type ApiUser, type UiUser } from './user';

export interface UiTask {
    uuid: string;
    title: string;
    status: unknown;
    creationDate: UnixTimestamp;
    deadlineDate: UnixTimestamp;
    assignees: UiUser[];
    priority: unknown;
}

export interface ApiTask {
    uuid: string;
    title: string;
    status: unknown;
    creationDate: number;
    deadlineDate: number;
    assignees: ApiUser[];
    priority: unknown;
}

export function taskUi(apiTask: ApiTask): UiTask {
    return {
        uuid: apiTask.uuid,
        title: apiTask.title,
        status: apiTask.status,
        creationDate: new UnixTimestamp(apiTask.creationDate),
        deadlineDate: new UnixTimestamp(apiTask.deadlineDate),
        assignees: apiTask.assignees.map(userUi),
        priority: apiTask.priority,
    };
}

export function taskApi(uiTask: UiTask): ApiTask {
    return {
        uuid: uiTask.uuid,
        title: uiTask.title,
        status: uiTask.status,
        creationDate: uiTask.creationDate.unixTimestamp,
        deadlineDate: uiTask.deadlineDate.unixTimestamp,
        assignees: uiTask.assignees.map(userApi),
        priority: uiTask.priority,
    };
}
