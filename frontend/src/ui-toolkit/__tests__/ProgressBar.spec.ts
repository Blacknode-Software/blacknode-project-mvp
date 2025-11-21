import { describe, it, expect } from 'vitest';

import { mount } from '@vue/test-utils';
import { ProgressBar } from '../';

describe('UI Toolkit | Progress Bar', () => {
    it('renders properly', () => {
        const wrapper = mount(ProgressBar, {
            props: { value: 50, maxValue: 100 },
        });
        expect(wrapper.isVisible()).toBeTruthy();
    });

    it('renders left slot', () => {
        const wrapper = mount(ProgressBar, {
            props: { value: 50, maxValue: 100 },
            slots: { left: 'left slot' },
        });
        expect(wrapper.text()).toContain('left slot');
    });

    it('renders right slot', () => {
        const wrapper = mount(ProgressBar, {
            props: { value: 50, maxValue: 100 },
            slots: { left: 'right slot' },
        });
        expect(wrapper.text()).toContain('right slot');
    });
});
