import { Menu, MenuButton, MenuItem, MenuItems, Transition } from '@headlessui/react';
import {
  ArrowRightOnRectangleIcon,
  ChatBubbleLeftEllipsisIcon,
  ChevronDownIcon,
  Cog6ToothIcon,
  MegaphoneIcon,
  UserIcon
} from '@heroicons/react/24/outline';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuthStore } from '../store/useAuthStore';

const ProfileDropdown = ({ imageUrl, logout }) => {
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="relative">
      <Menu>
        <MenuButton
          className="flex items-center space-x-2 focus:outline-none"
          onClick={() => setIsOpen(!isOpen)}>
          <img
            src={imageUrl}
            alt="Profile"
            className="w-10 h-10 rounded-full border-2 border-gray-300 hover:border-blue-500 cursor-pointer"
          />
          <ChevronDownIcon className="w-5 h-5 text-gray-600" />
        </MenuButton>
        <Transition
          show={isOpen}
          enter="transition ease-out duration-100"
          enterFrom="transform opacity-0 scale-95"
          enterTo="transform opacity-100 scale-100"
          leave="transition ease-in duration-75"
          leaveFrom="transform opacity-100 scale-100"
          leaveTo="transform opacity-0 scale-95">
          <MenuItems className="absolute right-0 mt-2 w-48 bg-white border border-gray-200 rounded-lg shadow-lg py-2 z-50">
            <MenuItem>
              {({ active }) => (
                <button
                  className={`${
                    active ? 'bg-gray-100' : ''
                  } flex w-full items-center px-4 py-2 text-gray-700`}>
                  <UserIcon className="w-5 h-5 mr-3" /> Profile
                </button>
              )}
            </MenuItem>
            <MenuItem>
              {({ active }) => (
                <button
                  className={`${
                    active ? 'bg-gray-100' : ''
                  } flex w-full items-center px-4 py-2 text-gray-700`}>
                  <Cog6ToothIcon className="w-5 h-5 mr-3" /> Settings
                </button>
              )}
            </MenuItem>
            <MenuItem>
              {({ active }) => (
                <button
                  onClick={() => {
                    logout();
                    navigate('/login');
                  }}
                  className={`${
                    active ? 'bg-red-100 text-red-600' : 'text-gray-700'
                  } flex w-full items-center px-4 py-2`}>
                  <ArrowRightOnRectangleIcon className="w-5 h-5 mr-3" /> Logout
                </button>
              )}
            </MenuItem>
          </MenuItems>
        </Transition>
      </Menu>
    </div>
  );
};

const Navbar = () => {
  const navigate = useNavigate();

  const { user, logout } = useAuthStore();
  return (
    <nav className="fixed top-0 w-full bg-white shadow-md py-3 px-6 flex justify-between items-center z-50 h-16">
      <div
        className="text-xl font-semibold text-gray-800 hover:text-blue-600"
        onClick={() => navigate('/')}>
        Patizone
      </div>

      {/* Orta kısım: Menü Linkleri */}
      <div className="flex space-x-6 text-gray-800 font-medium">
        <Link to="/ad" className="flex items-center space-x-2 hover:text-blue-600">
          <MegaphoneIcon className="w-5 h-5" />
          <span>Create Ad</span>
        </Link>
        <Link to="/messages" className="flex items-center space-x-2 hover:text-blue-600">
          <ChatBubbleLeftEllipsisIcon className="w-5 h-5" />
          <span>Messages</span>
        </Link>
      </div>

      {/* Sağ taraf: Profil DropDown */}
      <ProfileDropdown imageUrl={user?.imageUrl} logout={logout} />
    </nav>
  );
};

export default Navbar;
