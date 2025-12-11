import { describe, it, expect } from 'vitest';

import { mount } from '@vue/test-utils';
import { FixedBackground } from '..';

describe('Components | Priority Text', () => {
    it('renders properly', () => {
        const wrapper = mount(FixedBackground);
        expect(wrapper.isVisible()).toBeTruthy();
    });
});
