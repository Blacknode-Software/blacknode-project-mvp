import type { UnixTimestamp } from '@/utils';

export interface Task {
    id: string;
    title: string;
    description: string;
    priority: number;
    beginAt: UnixTimestamp;
    endAt: UnixTimestamp;
    statusId: string;
    progress?: number;
}
