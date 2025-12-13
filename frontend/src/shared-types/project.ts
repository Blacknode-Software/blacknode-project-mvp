import type { Channel } from './channel';

export interface Project {
    uuid: string;
    name: string;
    channels: Channel[];
}
