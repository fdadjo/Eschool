export const navigation = [
  {
    name: 'Dashboard',
    url: '/dashboard',
    icon: 'icon-speedometer',
    badge: {
      variant: 'success',
      text: 'NEW'
    }
  },
  {
    title: true,
    name: 'Absences'
  },
  {
    name: 'Absences',
    url: '/theme/colors',
    icon: 'icon-bell'
  },
  {
    name: 'Emploi de Temps',
    url: '/theme/colors',
    icon: 'icon-calendar'
  },
  {
    name: 'Unité',
    url: '/theme/typography',
    icon: 'icon-settings'
  },
  {
    name: 'Examens',
    url: '/theme/typography',
    icon: 'icon-notebook'
  },
  {
    title: true,
    name: 'Examens'
  },
  {
    name: 'Classes',
    url: '/charts',
    icon: 'icon-home'
  },
  {
    name: 'Enseignants',
    url: '/forms',
    icon: 'icon-user'/*,
    children: [
      {
        name: 'Basic Forms',
        url: '/forms/basic-forms',
        icon: 'icon-note'
      },
      {
        name: 'Advanced Forms',
        url: '/forms/advanced-forms',
        icon: 'icon-note'
      },
    ]*/
  },
  {
    name: 'Elèves',
    url: '/google-maps',
    icon: 'icon-people'/*,
    badge: {
      variant: 'info',
      text: 'NEW'
    }*/
  },
  {
    name: 'Devoirs',
    url: '/icons',
    icon: 'icon-screen-desktop'/*,
    children: [
      {
        name: 'Flags',
        url: '/icons/flags',
        icon: 'icon-star',
        badge: {
          variant: 'success',
          text: 'NEW'
        }
      },
      {
        name: 'Font Awesome',
        url: '/icons/font-awesome',
        icon: 'icon-star',
        badge: {
          variant: 'secondary',
          text: '4.7'
        }
      },
      {
        name: 'Simple Line Icons',
        url: '/icons/simple-line-icons',
        icon: 'icon-star'
      }
    ]*/
  },
  {
    name: 'Comptabilité',
    url: '/notifications',
    icon: 'icon-credit-card'/*,
    children: [
      {
        name: 'Alerts',
        url: '/notifications/alerts',
        icon: 'icon-bell'
      },
      {
        name: 'Modals',
        url: '/notifications/modals',
        icon: 'icon-bell'
      },
      {
        name: 'Toastr',
        url: '/notifications/toastr',
        icon: 'icon-bell'
      }
    ]*/
  }
];
