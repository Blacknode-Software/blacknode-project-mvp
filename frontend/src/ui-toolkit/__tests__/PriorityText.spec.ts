import { describe, it, expect } from 'vitest';

import { mount } from '@vue/test-utils';
import { PriorityText } from '..';

describe('UI Toolkit | Priority Text', () => {
    it('renders properly', () => {
        const wrapper = mount(PriorityText);
        expect(wrapper.isVisible()).toBeTruthy();
    });

    it('renders with proper text', () => {
        const wrapper = mount(PriorityText, {
            props: {
                value: 0,
            },
        });
        expect(wrapper.text()).toContain('Low');
    });
});
