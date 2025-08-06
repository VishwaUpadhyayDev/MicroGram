import { Home, MessageCircle, Plus } from 'lucide-react';

function BottomNav({ activeTab, setActiveTab }) {
  const navItems = [
    { id: 'chats', icon: MessageCircle, label: 'Chat' },
    { id: 'feed', icon: Home, label: 'Feed' },
    { id: 'create', icon: Plus, label: 'Post' }
  ];

  return (
    <nav className="lg:hidden fixed bottom-0 left-0 right-0 z-50" style={{ backgroundColor: 'var(--bg-light)', borderTop: '1px solid var(--border)' }}>
      <div className="flex justify-around py-2">
        {navItems.map(({ id, icon: Icon, label }) => (
          <button
            key={id}
            onClick={() => setActiveTab(id)}
            className="flex flex-col items-center py-2 px-3 rounded-lg transition-colors"
            style={{ 
              color: activeTab === id ? 'var(--primary)' : 'var(--text-muted)',
              backgroundColor: activeTab === id ? 'var(--highlight)' : 'transparent'
            }}
          >
            <Icon size={20} />
            <span className="text-xs mt-1">{label}</span>
          </button>
        ))}
      </div>
    </nav>
  );
}

export default BottomNav;