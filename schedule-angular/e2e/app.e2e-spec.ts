import { ScheduleAngular2Page } from './app.po';

describe('schedule-angular2 App', () => {
  let page: ScheduleAngular2Page;

  beforeEach(() => {
    page = new ScheduleAngular2Page();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
