import { describe, it, expect } from 'vitest';

import { mount } from '@vue/test-utils';
import { TimestampDate } from '..';
import { UnixTimestamp } from '@/utils';

const dateFormatter = new Intl.DateTimeFormat('en-US', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
});

describe('UI Toolkit | Timestamp Date', () => {
    it('renders with proper date', () => {
        const wrapper = mount(TimestampDate, {
            props: {
                timestmap: new UnixTimestamp(1763759315),
                formatter: dateFormatter,
            },
        });
        expect(wrapper.text()).toContain('11/21/2025');
    });
});
