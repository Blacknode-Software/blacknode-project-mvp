import { describe, it, expect } from 'vitest';

import { mount } from '@vue/test-utils';
import { UniSidebar } from '..';

describe('Layout | Uni Sidebar', () => {
    it('renders properly', () => {
        const wrapper = mount(UniSidebar);
        expect(wrapper.isVisible()).toBeTruthy();
    });

    it('renders with proper text', () => {
        const wrapper = mount(UniSidebar, {
            slots: {
                default: 'Text',
            },
        });
        const text = wrapper.text();
        expect(text).toContain('Text');
    });
});
