import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';
import type { OverlayOptions } from './types';

export interface Spec extends TurboModule {
  applyOverlay(options: OverlayOptions): Promise<string>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('VideoOverlay');
