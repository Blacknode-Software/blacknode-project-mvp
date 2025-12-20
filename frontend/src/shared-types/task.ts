import type { UnixTimestamp } from '@/utils';

export interface Task {
    id: string;
    title: string;
    description: string;
    timestamp: UnixTimestamp;
    priority: number;
}
